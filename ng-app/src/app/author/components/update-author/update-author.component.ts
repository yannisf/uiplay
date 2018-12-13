import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Author} from "../../author";
import {AuthorService} from "../../author.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-update-author',
  templateUrl: './update-author.component.html',
  styleUrls: ['./update-author.component.scss']
})
export class UpdateAuthorComponent implements OnInit {

  @Input() author: Author;
  @Output() saved = new EventEmitter<Author>();
  @ViewChild('updateAuthor') private input: ElementRef;
  private originalAuthor: Author;

  authorForm: FormGroup;

  constructor(private authorService: AuthorService) {
  }

  get name() {
    return this.authorForm.get('name');
  }

  ngOnInit() {
    this.originalAuthor = JSON.parse(JSON.stringify(this.author));
    this.input.nativeElement.focus();
    this.authorForm = new FormGroup({
      name: new FormControl(this.author.name, [
        Validators.required,
        Validators.minLength(2)])
    });
  }

  save(): void {
    this.author.name = this.authorForm.value.name;
    this.authorService.insert(this.author).subscribe(author => {
      this.saved.emit(author);
    });
  }

  cancel(): void {
    this.saved.emit(this.originalAuthor);
  }

}
