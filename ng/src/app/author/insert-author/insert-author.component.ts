import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Author} from '../author';
import {AuthorService} from '../author.service';

@Component({
  selector: 'app-insert-author',
  templateUrl: './insert-author.component.html',
  styleUrls: ['./insert-author.component.scss']
})
export class InsertAuthorComponent implements OnInit {

  public author: Author;
  public loading: boolean;
  @ViewChild('inputAuthor')
  private input: ElementRef;

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
    this.author = new Author();
    this.input.nativeElement.focus();
  }

  insert(): void {
    this.loading = true;
    this.authorService.insert(this.author).subscribe(author => {
      this.authorService.addedAuthor();
      this.author = new Author();
      this.loading = false;
      this.input.nativeElement.focus();
    });
  }

}
