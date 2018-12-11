import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorTypeaheadComponent } from './author-typeahead.component';

describe('AuthorTypeaheadComponent', () => {
  let component: AuthorTypeaheadComponent;
  let fixture: ComponentFixture<AuthorTypeaheadComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorTypeaheadComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorTypeaheadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
