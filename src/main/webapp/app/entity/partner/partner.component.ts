import { Component, OnInit, ViewEncapsulation, ChangeDetectionStrategy } from '@angular/core';

@Component({
  selector: 'partner',
  templateUrl: './app/entity/partner/partner.component.html',
  styleUrls: ['./app/entity/partner/partner.component.css'],
  encapsulation: ViewEncapsulation.Emulated,
  changeDetection: ChangeDetectionStrategy.Default
})
export class PartnerComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
