import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsertBookComponent } from './insert-book.component';

describe('InsertBookComponent', () => {
  let component: InsertBookComponent;
  let fixture: ComponentFixture<InsertBookComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsertBookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsertBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
