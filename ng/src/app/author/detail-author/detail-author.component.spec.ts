import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailAuthorComponent } from './detail-author.component';

describe('DetailAuthorComponent', () => {
  let component: DetailAuthorComponent;
  let fixture: ComponentFixture<DetailAuthorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DetailAuthorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DetailAuthorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
