import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Author} from "../../author";
import {Router} from "@angular/router";

@Component({
  selector: 'app-added-author-alert',
  templateUrl: './added-author-alert.component.html'
})
export class AddedAuthorAlertComponent {

  @Input() author: Author;
  @Output() authorCreatedClear = new EventEmitter<void>();

  constructor(private router: Router) {
  }

  openAuthorCreated() {
    this.authorCreatedClear.emit();
    this.router.navigate(['/author', this.author.id]);
    return false;
  }

  onClose(): void {
    this.authorCreatedClear.emit();
  }

}
