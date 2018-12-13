import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AuthorShellComponent} from './author-shell.component';

describe('AuthorShellComponent', () => {
  let component: AuthorShellComponent;
  let fixture: ComponentFixture<AuthorShellComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorShellComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorShellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
