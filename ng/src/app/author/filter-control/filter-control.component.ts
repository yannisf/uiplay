import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {PopoverDirective} from "ngx-bootstrap";
import {AuthorService} from "../author.service";

@Component({
  selector: 'app-filter-control',
  templateUrl: './filter-control.component.html',
  styleUrls: ['./filter-control.component.scss']
})
export class FilterControlComponent implements OnInit {

  // isOpen = false;
  @Output() updated = new EventEmitter<string>();
  _el : PopoverDirective;

  @ViewChild('pop')
  set el(el: PopoverDirective) {
    this._el = el;
  }

  constructor(private authorService: AuthorService) {
  }

  ngOnInit() {
  }

  set filter(filter: string) {
    this.authorService.filter = filter;
  }

  get filter() {
    return this.authorService.filter;
  }

  onOk($event) {
    console.log('onOk:', $event);
    this.updated.emit(this.filter);
    this._el.hide();
  }

  onCancel($event) {
    console.log('onCancel:', $event);
    this._el.hide();
  }

  onClear($event) {
    console.log('onClear:', $event);
    this.filter = '';
    this.updated.emit(this.filter);
    this._el.hide();
  }
}
