import { Injectable } from '@angular/core';
import {ExerciseFacility} from "../exercise-facility/exercise-facility";
import { Http, Response } from '@angular/http';
import { Observable }     from 'rxjs/Rx';

@Injectable()
export class FacilitySearchService {
    constructor(private http: Http) {}

    search(term: string): Observable<ExerciseFacility[]> {
        console.log('FacilitySearchService.sercing for facilities');
        return this.http
            .get(`api/filter-by-location/?term=${term}&type=circle`)
            .map((r: Response) => r.json() as ExerciseFacility[]);
    }

}
