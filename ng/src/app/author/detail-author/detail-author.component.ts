import {Component, OnInit} from '@angular/core';
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

  public author: Author;

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

  goBack(): void {
    this.location.back();
  }

}
