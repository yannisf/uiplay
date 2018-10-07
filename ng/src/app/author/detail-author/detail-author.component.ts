import {Component, HostListener, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
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
  private originalAuthor: Author;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
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

  @HostListener('document:keydown.e')
  edit(): void {
    this.editMode = true;
    this.originalAuthor = JSON.parse(JSON.stringify(this.author));
  }

  goBack(): void {
    this.location.back();
    // this.router.navigateByUrl('/');
  }

  saved($event: boolean) {
    this.editMode = false;
    if (!$event) {
      this.author = this.originalAuthor;
    }
    this.originalAuthor = null;
  }
}
