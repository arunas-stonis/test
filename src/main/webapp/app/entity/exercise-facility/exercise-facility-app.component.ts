import { Component, OnInit } from '@angular/core';
import { ExerciseFacilityService } from './exercise-facility.service';
import { ExerciseFacility } from './exercise-facility';

@Component({
  selector: 'exercise-facility',
  templateUrl: 'app/entity/exercise-facility/exercise-facility-app.component.html',
  styleUrls: ['app/entity/exercise-facility/exercise-facility-app.component.css'],
  providers: [ExerciseFacilityService]
})
export class ExerciseFacilityAppComponent implements OnInit {

  ngOnInit() {
    this.getAllFacilitiess();
  }

  selectedFacility: ExerciseFacility = new ExerciseFacility();
  facilities: ExerciseFacility[] = [];

  constructor(private facilityService: ExerciseFacilityService) {
      console.log('ExerciseFacilityAppComponent.constructor');
  }

  addFacility(): void  {
    console.log('ExerciseFacilityAppComponent.addFacility');
    this.selectedFacility = new ExerciseFacility();
    // this.facilityService.addFacility(newFacility)
    //   .then(facility => {
    //     this.facilities.push(facility);
    //     this.selectedFacility = facility;
    //   });
  }

    updateFacility(facility: ExerciseFacility): void  {
        if (!facility.id) {
            //ading new facility
            this.facilityService.addFacility(facility)
                .then(facility => {
                    this.facilities.push(facility);
                    this.selectedFacility = facility;
                });
        } else {
            //updating an existing facility
            this.facilityService.updateFacilityById(facility)
                .then(facility => {
                    // this.facilities.push(facility);
                    this.selectedFacility = facility;
                });
        }
    }

  removeFacility(facility: ExerciseFacility): void  {
    this.facilityService
      .deleteFacilityById(facility.id)
      .then(() => {
        this.facilities = this.facilities.filter(f => f !== facility);
        if (this.selectedFacility === facility) { this.selectedFacility = new ExerciseFacility(); }
      });
  }

  getAllFacilitiess(): void {
    this.facilityService.getAllFacilities().then(facilities => this.facilities = facilities);
  }

  onSelect(facility: ExerciseFacility): void {
    console.log('ExerciseFacilityAppComponent.onSelect');
    this.selectedFacility = facility;
  }

}
