import { Component, OnInit, OnDestroy } from '@angular/core';
import { Author } from '../author';
import { AuthorService } from '../author.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-list-authors',
  templateUrl: './list-authors.component.html',
  styleUrls: ['./list-authors.component.scss']
})
export class ListAuthorsComponent implements OnInit, OnDestroy {
  subscription: Subscription;
  authors: Author[];

  constructor(private authorService: AuthorService) {
    this.subscription = this.authorService.receiveAddedAuthor().subscribe(m => {
      this.list();
    });
  }

  ngOnInit() {
    this.list();
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  private list(): void {
    this.authorService.list().subscribe(data => {
      this.authors = data;
    });
  }

}
