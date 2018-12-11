import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {Author} from "../author";
import {AuthorService} from "../author.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-detail-author',
  templateUrl: './detail-author.component.html',
  styleUrls: ['./detail-author.component.scss']
})
export class DetailAuthorComponent implements OnInit, OnDestroy {

  public author: Author = new Author();
  public editMode: boolean = false;
  private subscription: Subscription;

  constructor(private route: ActivatedRoute,
              private authorService: AuthorService) {
  }

  ngOnInit() {
    //Trick to watch for updates on the same route
    this.subscription = this.route.params.subscribe(params => {
      this.getAuthor(params['id']);
    });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  getAuthor(id: number): void {
    this.authorService.fetch(id)
      .subscribe(author => this.author = author);
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
