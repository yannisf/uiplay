import {Component, Input, OnInit} from '@angular/core';
import {Author} from "../../author";

@Component({
  selector: 'app-added-author-alert',
  templateUrl: './added-author-alert.component.html',
  styleUrls: ['./added-author-alert.component.scss']
})
export class AddedAuthorAlertComponent implements OnInit {

  @Input() author: Author;

  constructor() {
  }

  ngOnInit() {
  }

}
