import {Component, HostListener, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Location} from "@angular/common";

import {Author} from "../author";
import {AuthorService} from "../author.service";

@Component({
  selector: 'app-detail-author',
  templateUrl: './detail-author.component.html',
  styleUrls: ['./detail-author.component.scss']
})
export class DetailAuthorComponent implements OnInit {

  public author: Author = new Author();
  public editMode: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private authorService: AuthorService) {
  }

  ngOnInit() {
    this.getAuthor();
  }

  getAuthor(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.authorService.fetch(id)
      .subscribe(author => this.author = author);
  }

  @HostListener('document:keydown.e', ['$event'])
  onKeydownHandler(event: KeyboardEvent): boolean {
    const result = this.editMode;
    this.edit();
    return result;
  }

  edit(): void {
    this.editMode = true;
  }

  saved($event: Author) {
    this.editMode = false;
    this.author = $event;
  }

  goBack(): void {
    this.location.back();
  }

}
