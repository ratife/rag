import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DocumentService } from '../../services/document.service';

@Component({
  selector: 'app-document-upload',
  templateUrl: './document-upload.component.html',
  styleUrls: ['./document-upload.component.css']
})
export class DocumentUploadComponent {
  uploadForm: FormGroup;
  selectedFile: File | null = null;
  uploading = false;
  success = '';
  error = '';

  constructor(
    private fb: FormBuilder,
    private documentService: DocumentService,
    private router: Router
  ) {
    this.uploadForm = this.fb.group({
      file: ['', Validators.required]
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      this.selectedFile = input.files[0];
    }
  }

  onSubmit(): void {
    if (this.uploadForm.invalid || !this.selectedFile) {
      return;
    }

    this.uploading = true;
    this.success = '';
    this.error = '';

    const formData = new FormData();
    formData.append('file', this.selectedFile);

    this.documentService.uploadDocument(formData).subscribe({
      next: (response) => {
        this.success = 'Document uploaded successfully!';
        this.uploading = false;
        setTimeout(() => {
          this.router.navigate(['/documents']);
        }, 2000);
      },
      error: (err) => {
        this.error = 'Error uploading document: ' + err.message;
        this.uploading = false;
      }
    });
  }
}