import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartShoesComponent } from './cart-shoes.component';

describe('CartShoesComponent', () => {
  let component: CartShoesComponent;
  let fixture: ComponentFixture<CartShoesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CartShoesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CartShoesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
