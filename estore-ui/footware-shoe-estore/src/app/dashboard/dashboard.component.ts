import { Component, OnInit } from '@angular/core';
import { Shoe } from '../interfaces/shoe';
import { ShoeService } from '../services/shoe.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  shoes: Shoe[] = [];

  constructor(private shoeService: ShoeService) { }

  ngOnInit(): void {
    this.getInventory();
  }

  getInventory(): void {
    this.shoeService.getInventory().subscribe(shoes => this.shoes = shoes.slice(1,5));
  }

}
