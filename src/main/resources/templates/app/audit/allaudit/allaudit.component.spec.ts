import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllauditComponent } from './allaudit.component';

describe('AllauditComponent', () => {
  let component: AllauditComponent;
  let fixture: ComponentFixture<AllauditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllauditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllauditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
