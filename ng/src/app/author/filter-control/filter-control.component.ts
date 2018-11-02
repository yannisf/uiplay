import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {PopoverDirective} from "ngx-bootstrap";
import {AuthorService} from "../author.service";

@Component({
  selector: 'app-filter-control',
  templateUrl: './filter-control.component.html',
  styleUrls: ['./filter-control.component.scss']
})
export class FilterControlComponent implements OnInit {

  @Output() updated = new EventEmitter<string>();
  private _el: PopoverDirective;

  @ViewChild('pop')
  set el(el: PopoverDirective) {
    this._el = el;
  }

  @ViewChild('inputFilter')
  set inputElement(inputElement: ElementRef<HTMLInputElement>) {
    if (inputElement) {
      inputElement.nativeElement.focus();
    }
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
    this.updated.emit(this.filter);
    this._el.hide();
  }

  onClear($event) {
    this.filter = '';
    this.updated.emit(this.filter);
    this._el.hide();
  }

}
