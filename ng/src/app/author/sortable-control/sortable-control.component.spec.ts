import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {SortableControlComponent} from './sortable-control.component';

describe('SortableControlComponent', () => {
  let component: SortableControlComponent;
  let fixture: ComponentFixture<SortableControlComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [SortableControlComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SortableControlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
