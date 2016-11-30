import { Injectable } from '@angular/core';
import { Http, Response, Headers } from '@angular/http';
import { ExerciseFacility } from './exercise-facility';
import { Observable } from 'rxjs/Observable'

import 'rxjs/add/operator/toPromise';

@Injectable()
export class ExerciseFacilityService {

  private apiUrl = 'api/exercise-facilities';
  private headers: Headers;

  constructor(private http: Http) {
      this.headers = new Headers();
      this.headers.append('Content-Type', 'application/json');
      this.headers.append('Accept', 'application/json');
  }

  facilities: ExerciseFacility[] = [];

  addFacility(facility: ExerciseFacility): Promise<ExerciseFacility> {
    return this.http
      .post(this.apiUrl, JSON.stringify(facility), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as ExerciseFacility)
      .catch(this.handleError);
  }

  deleteFacilityById(id: number): Promise<void> {
    const url = `${this.apiUrl}/${id}`;
    return this.http.delete(url, {headers: this.headers})
      .toPromise()
      .then(() => null)
      .catch(this.handleError);
  }

  updateFacilityById(facility: ExerciseFacility): Promise<ExerciseFacility> {
    //const url = `${this.apiUrl}/${facility.id}`;
      console.log(this.apiUrl);
      console.log(JSON.stringify(facility));
    return this.http
      .put(this.apiUrl, JSON.stringify(facility), {headers: this.headers})
      .toPromise()
      .then(() => facility)
      .catch(this.handleError);
  }

  getAllFacilities(): Promise<ExerciseFacility[]> {
    // return this.facilities;
    console.log('ExerciseFacilityService.getAllFacilities');
    return this.http.get(this.apiUrl)
               .toPromise()
               .then(response => response.json() as ExerciseFacility[])
               .catch(this.handleError);
  }

  getFacilityById(id: number): Promise<ExerciseFacility> {
    return this.getAllFacilities()
               .then(facilities => facilities.find(facility => facility.id === id));
  }

  handleError(error: any): Promise<any> {
    console.error('An error occurred', error);
    return Promise.reject(error.message || error);
  }
}
