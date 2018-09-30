import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsertAuthorComponent } from './insert-author.component';

describe('InsertAuthorComponent', () => {
  let component: InsertAuthorComponent;
  let fixture: ComponentFixture<InsertAuthorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsertAuthorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsertAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
