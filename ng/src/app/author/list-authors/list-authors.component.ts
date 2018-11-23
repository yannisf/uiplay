import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {Author} from '../author';
import {AuthorService} from '../author.service';
import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';
import {PageChangedEvent} from "ngx-bootstrap";
import {Subscription} from "rxjs";
import {Sort} from "../sort";

@Component({
  selector: 'app-list-authors',
  templateUrl: './list-authors.component.html',
  styleUrls: ['./list-authors.component.scss']
})
export class ListAuthorsComponent implements OnInit, OnDestroy {

  private subscription: Subscription;
  authors: Author[];
  modalRef: BsModalRef;
  selectedAuthorId: number;
  totalItems: number;

  constructor(private authorService: AuthorService,
              private modalService: BsModalService) {
  }

  ngOnInit() {
    this.page();
    this.subscription = this.authorService.receiveAddedAuthor().subscribe(m => {
      this.page();
    });
  }

  ngOnDestroy() { // <- Do I have to do this every time? What about hostkeys
    this.subscription.unsubscribe();
  }

  delete(): void {
    this.authorService.delete(this.selectedAuthorId).subscribe(data => {
      this.selectedAuthorId = null;
      this.modalRef.hide();
      this.page();
    });
  }

  pageChanged($event: PageChangedEvent) {
    this.authorService.currentPage = $event.page - 1;
    this.page();
  }

  private page(): void {
    this.authorService.page(this.authorService.currentPage).subscribe(data => {
      this.authors = data.values;
      this.totalItems = data.totalElements;
    });
  }

  openModal(template: TemplateRef<any>, authorId) {
    this.selectedAuthorId = authorId;
    this.modalRef = this.modalService.show(template);
  }

  onChangedSort($event: Sort) {
    this.authorService.sort = Sort[$event];
    this.page();
  }

  onFilterUpdated($event) {
    this.page();
  }

  clearFilter() {
    this.authorService.filter = '';
    this.page();
  }

  hasFilter() {
    return !!this.authorService.filter;
  }
}
