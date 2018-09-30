import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListAuthorsComponent } from './list-authors.component';

describe('ListAuthorsComponent', () => {
  let component: ListAuthorsComponent;
  let fixture: ComponentFixture<ListAuthorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListAuthorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListAuthorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
