import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Message, Response } from '../models/message.model';

@Injectable({
  providedIn: 'root'
})
export class MessageService {
  private apiUrl = 'http://localhost:8080/messages';

  constructor(private http: HttpClient) { }

  getMessages(conversationId: string): Observable<Message[]> {
    const params = new HttpParams().set('conversationID', conversationId);
    return this.http.get<Message[]>(this.apiUrl, { params });
  }

  sendMessage(documentId: string, message: string, conversationId: string): Observable<Response> {
    const params = new HttpParams()
      .set('documentIndex', documentId)
      .set('message', message)
      .set('conversationID', conversationId);
    
    return this.http.post<Response>(`${this.apiUrl}/chat`, null, { params });
  }
}