export interface Message {
  id?: string;
  content: string;
  timestamp: Date;
  isUserMessage: boolean;
  conversationId: string;
  response?: Response;
}

export interface Response {
  id?: string;
  content: string;
  timestamp?: Date;
  sources?: string[];
}