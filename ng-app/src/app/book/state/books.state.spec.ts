import {Book} from "../book";
import {sortBooks} from "./book.reducer";

describe('BooksState', () => {

  beforeEach(() => {
    console.log('Init');
  });

  it('should sort books according to keys', () => {
    //arrange
    let books = [_createTestBook(1), _createTestBook(2), _createTestBook(3)];
    let keys = [3, 1, 2];

    // act
    let sortedBooks = sortBooks(books, keys);

    // assert
    expect(sortedBooks[0]).toEqual(books[2]);
    expect(sortedBooks[1]).toEqual(books[0]);
    expect(sortedBooks[2]).toEqual(books[1]);
  });

});

function _createTestBook(id: number) {
  let book1 = new Book();
  book1.id = id;
  book1.title = `title${id}`;
  return book1;
}
