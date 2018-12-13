import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AddedAuthorAlertComponent} from './added-author-alert.component';

describe('AddedAuthorAlertComponent', () => {
  let component: AddedAuthorAlertComponent;
  let fixture: ComponentFixture<AddedAuthorAlertComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AddedAuthorAlertComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddedAuthorAlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
