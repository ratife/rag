import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Message, Response } from '../../models/message.model';
import { MessageService } from '../../services/message.service';
//import { v4 as uuid4 } from 'uuid';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {
  documentIndex: string = '';
  conversationId: string = '';
  messages: Message[] = [];
  chatForm: FormGroup;
  loading = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private messageService: MessageService
  ) {
    this.chatForm = this.fb.group({
      message: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.documentIndex = this.route.snapshot.paramMap.get('documentIndex') || '';
    // Generate a new conversation ID if not provided
    this.loadMessages();
  }

  loadMessages(): void {
    if (!this.conversationId) return;

    this.loading = true;
    this.messageService.getMessages(this.conversationId).subscribe({
      next: (data) => {
        this.messages = data;
        this.loading = false;
        this.scrollToBottom();
      },
      error: (err) => {
        this.error = 'Error loading messages: ' + err.message;
        this.loading = false;
      }
    });
  }

  sendMessage(): void {
    if (this.chatForm.invalid) return;

    const messageText = this.chatForm.get('message')?.value;
    
    // Add user message to the UI immediately
    const userMessage: Message = {
      content: messageText,
      timestamp: new Date(),
      isUserMessage: true,
      conversationId: this.conversationId
    };
    
    this.messages.push(userMessage);
    this.chatForm.reset();
    this.scrollToBottom();
    
    // Send message to the server
    this.loading = true;
    this.messageService.sendMessage(this.documentIndex, messageText, this.conversationId).subscribe({
      next: (response) => {
        // Add system response to the UI
        const systemMessage: Message = {
          id: response.id ,
          content: response.content,
          timestamp: response.timestamp || new Date(),
          isUserMessage: false,
          conversationId: this.conversationId
        };
        
        this.messages.push(systemMessage);
        this.loading = false;
        this.scrollToBottom();
      },
      error: (err) => {
        this.error = 'Error sending message: ' + err.message;
        this.loading = false;
      }
    });
  }

  scrollToBottom(): void {
    setTimeout(() => {
      const chatContainer = document.querySelector('.chat-container');
      if (chatContainer) {
        chatContainer.scrollTop = chatContainer.scrollHeight;
      }
    }, 100);
  }
}