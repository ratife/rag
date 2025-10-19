import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Conversation } from 'src/app/models/conversation.model';
import { ConversationService } from 'src/app/services/conversation.service';

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['./conversation.component.css']
})
export class ConversationComponent {
  conversations: Conversation[] = [];
  loading = false;
  error = '';

  constructor(
    private conversationService: ConversationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadDocuments();
  }

  loadDocuments(): void {
    this.loading = true;
    this.error = '';
    
    this.conversationService.getConversations().subscribe({
      next: (data) => {
        this.conversations = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error loading documents: ' + err.message;
        this.loading = false;
      }
    });
  }

  openConversation(convId: string): void {
    console.log('Navigating to conversation ID:', convId);
    this.router.navigate(['/conversation', convId]);
  }

}
