import {Component, ElementRef, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {PopoverDirective} from "ngx-bootstrap";

@Component({
  selector: 'app-filter-control',
  templateUrl: './filter-control.component.html',
  styleUrls: ['./filter-control.component.scss']
})
export class FilterControlComponent {

  private _el: PopoverDirective;
  @Input() filter: string;
  @Output() filterChange = new EventEmitter<string>();

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

  onOk() {
    this.filterChange.emit(this.filter);
    this._el.hide();
  }

  onClear() {
    this.filterChange.emit('');
    this._el.hide();
  }

}
