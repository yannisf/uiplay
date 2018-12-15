export class Book {
  id: number;
  title: string;
}

export class AuthorBook {
  authorId: number;
  book: Book;
}

export interface AuthorIdBookId {
  authorId: number;
  bookId: number;
}
