/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { FacilitySearchService } from './facility-search.service';

describe('FacilitySearchService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [FacilitySearchService]
    });
  });

  it('should ...', inject([FacilitySearchService], (service: FacilitySearchService) => {
    expect(service).toBeTruthy();
  }));
});
