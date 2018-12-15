import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';

import {Author} from "../../author";
import {select, Store} from "@ngrx/store";
import {AppState} from "../../../state/app.state";
import {LoadAuthorDetails} from "../../state/authors.actions";
import {getSelectedAuthor} from "../../state/author.reducer";
import {take, takeWhile} from "rxjs/operators";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-detail-author',
  templateUrl: './detail-author.component.html',
  styleUrls: ['./detail-author.component.scss']
})
export class DetailAuthorComponent implements OnInit, OnDestroy {

  author: Author = null;
  editMode: boolean = false;
  private componentActive = true;

  constructor(private store: Store<AppState>, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.params.pipe(
      take(1))
      .subscribe(params => {
        this.store.dispatch(new LoadAuthorDetails(+params['id']));
      });
    this.store.pipe(select(getSelectedAuthor),
      takeWhile(() => this.componentActive))
      .subscribe(author => this.author = author);
  }

  ngOnDestroy(): void {
    this.componentActive = false;
  }

  @HostListener('window:keyup.e', ['$event'])
  edit($event): boolean {
    if ($event !== undefined && $event !== null && $event.target.localName !== 'body') {
      return false;
    }
    this.editMode = true;
  }

  saved($event: Author) {
    this.editMode = false;
    this.author = $event;
  }

}
