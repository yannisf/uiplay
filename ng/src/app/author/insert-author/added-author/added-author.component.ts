import {Component, Input, OnInit} from '@angular/core';
import {Author} from "../../author";

@Component({
  selector: 'app-added-author',
  templateUrl: './added-author.component.html',
  styleUrls: ['./added-author.component.scss']
})
export class AddedAuthorComponent implements OnInit {

  @Input() author: Author;

  constructor() {
  }

  ngOnInit() {
  }

}
