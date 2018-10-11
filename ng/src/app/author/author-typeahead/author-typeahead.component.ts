import {Component, OnInit} from '@angular/core';
import {AuthorService} from "../author.service";
import {Author} from "../author";
import {Observable} from "rxjs";
import {mergeMap} from "rxjs/operators";
import {Router} from "@angular/router";

@Component({
  selector: 'app-author-typeahead',
  templateUrl: './author-typeahead.component.html',
  styleUrls: ['./author-typeahead.component.scss']
})
export class AuthorTypeaheadComponent implements OnInit {

  selected: string;
  authors: Author[];

  constructor(private authorService: AuthorService,
              private router: Router) {
  }

  ngOnInit() {
    this.authors = Observable.create((observer: any) => observer.next(this.selected))
      .pipe(mergeMap((token: string) => this.authorService.search(token)));
  }

  onSelect($event) {
    this.router.navigate(['author', $event.item.id]);
    this.selected = null;
  }

}
