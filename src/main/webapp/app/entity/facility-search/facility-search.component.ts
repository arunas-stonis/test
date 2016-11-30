import { Component, OnInit, ViewEncapsulation, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'facility-search',
  templateUrl: './app/entity/facility-search/facility-search.component.html',
  styleUrls: ['./app/entity/facility-search/facility-search.component.css'],
  encapsulation: ViewEncapsulation.Emulated,
  changeDetection: ChangeDetectionStrategy.Default
})
export class FacilitySearchComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
