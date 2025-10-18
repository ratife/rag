import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Document } from '../../models/document.model';
import { DocumentService } from '../../services/document.service';

@Component({
  selector: 'app-document-list',
  templateUrl: './document-list.component.html',
  styleUrls: ['./document-list.component.css']
})
export class DocumentListComponent implements OnInit {
  documents: Document[] = [];
  loading = false;
  error = '';

  constructor(
    private documentService: DocumentService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadDocuments();
  }

  loadDocuments(): void {
    this.loading = true;
    this.error = '';
    
    this.documentService.getAllDocuments().subscribe({
      next: (data) => {
        this.documents = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Error loading documents: ' + err.message;
        this.loading = false;
      }
    });
  }

  openChat(documentIndex: string): void {
    this.router.navigate(['/chat', documentIndex]);
  }
}