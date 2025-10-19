import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { DocumentListComponent } from './components/document-list/document-list.component';
import { DocumentUploadComponent } from './components/document-upload/document-upload.component';
import { ChatComponent } from './components/chat/chat.component';
import { ConversationComponent } from './components/conversation/conversation.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'documents', component: DocumentListComponent },
  { path: 'upload', component: DocumentUploadComponent },
  { path: 'chat/:documentIndex', component: ChatComponent },
  { path: 'conversation', component: ConversationComponent },
  { path: 'conversation/:conversationId', component: ChatComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }