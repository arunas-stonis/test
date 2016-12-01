import { Component, OnInit, ViewEncapsulation, ChangeDetectionStrategy } from '@angular/core';
import {ExerciseFacility} from "../exercise-facility/exercise-facility";
import {FacilitySearchService} from "./facility-search.service";
import { Subject } from 'rxjs/Subject';
import { Http, Response } from '@angular/http';
import { Observable }     from 'rxjs/Rx';

@Component({
  selector: 'facility-search',
  templateUrl: './app/entity/facility-search/facility-search.component.html',
  styleUrls: ['./app/entity/facility-search/facility-search.component.css']
})
export class FacilitySearchComponent implements OnInit {
    facilities: Observable<ExerciseFacility[]>;
    private searchTerms = new Subject<string>();

    constructor(
        private facilitySearchService: FacilitySearchService) {}

    ngOnInit(): void {
        this.facilities = this.searchTerms
            .debounceTime(300)        // wait for 300ms pause in events
            .distinctUntilChanged()   // ignore if next search term is same as previous
            .switchMap(term => term   // switch to new observable each time
                // return the http search observable
                ? this.facilitySearchService.search(term)
                // or the observable of empty facilities if no search term
                : Observable.of<ExerciseFacility[]>([]))
            .catch(error => {
                console.log('Failed searching for facilities. Error: ' + error);
                return Observable.of<ExerciseFacility[]>([]);
            });
    }

    // Push a search term into the observable stream.
    search(term: string): void {
        console.log('Searching for a new term: '+term);
        this.searchTerms.next(term);
    }

    select(facility: ExerciseFacility): void {
        let link = ['/exercise-facility', facility.id];
    }

}
