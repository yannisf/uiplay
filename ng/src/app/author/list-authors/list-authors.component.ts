import {Component, OnDestroy, OnInit, TemplateRef} from '@angular/core';
import {Author} from '../author';
import {AuthorService} from '../author.service';
import {Subscription} from 'rxjs';
import {BsModalService} from 'ngx-bootstrap/modal';
import {BsModalRef} from 'ngx-bootstrap/modal/bs-modal-ref.service';

@Component({
  selector: 'app-list-authors',
  templateUrl: './list-authors.component.html',
  styleUrls: ['./list-authors.component.scss']
})
export class ListAuthorsComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  authors: Author[];
  modalRef: BsModalRef;
  selectedAuthorId: number;
  loading: boolean;

  constructor(private authorService: AuthorService,
              private modalService: BsModalService) {
  }

  ngOnInit() {
    this.list();
    this.subscription = this.authorService.receiveAddedAuthor().subscribe(m => {
      this.list();
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  delete(): void {
    this.authorService.delete(this.selectedAuthorId).subscribe(data => {
      this.selectedAuthorId = null;
      this.modalRef.hide();
      this.list();
    });
  }

  private list(): void {
    this.loading = true;
    this.authorService.list().subscribe(data => {
      this.authors = data;
      this.loading = false;
    });
  }

  openModal(template: TemplateRef<any>, authorId) {
    this.selectedAuthorId = authorId;
    this.modalRef = this.modalService.show(template);
  }

}
