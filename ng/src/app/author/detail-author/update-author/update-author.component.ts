import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Author} from "../../author";
import {AuthorService} from "../../author.service";

@Component({
  selector: 'app-update-author',
  templateUrl: './update-author.component.html',
  styleUrls: ['./update-author.component.scss']
})
export class UpdateAuthorComponent implements OnInit {

  @Input() author: Author;
  @Output() saved = new EventEmitter<boolean>();
  @ViewChild('updateAuthor')
  private input: ElementRef;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
    this.input.nativeElement.focus();
  }

  save(): void {
    this.authorService.insert(this.author).subscribe(author => {
      this.saved.emit(true);
    });
  }

  cancel(): void {
    this.saved.emit(false);
  }

}
