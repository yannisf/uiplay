import {Component, OnInit} from '@angular/core';
import {Author} from '../author';
import {AuthorService} from '../author.service';

@Component({
  selector: 'app-insert-author',
  templateUrl: './insert-author.component.html',
  styleUrls: ['./insert-author.component.scss']
})
export class InsertAuthorComponent implements OnInit {

  author: Author;
  loading: boolean;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
    this.author = new Author();
  }

  insert(): void {
    this.loading = true;
    this.authorService.insert(this.author).subscribe(author => {
      this.authorService.addedAuthor();
      this.loading = false;
    });
  }

}
