import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UIRouterModule } from 'ui-router-ng2';

import { MyappSharedModule } from '../shared';

import {
    entityState,

    ExerciseFacilityAppComponent,
    ExerciseFacilityService,
    exerciseFacilityState,

    FacilitySearchComponent,
    FacilitySearchService,
    facilitySearchState,

    PartnerComponent,
    PartnerService,
    partnerState
} from './';

let ENTITY_STATES = [
    entityState,
    exerciseFacilityState,
    facilitySearchState,
    partnerState
];

@NgModule({
    imports: [
        MyappSharedModule,
        UIRouterModule.forChild({ states: ENTITY_STATES })
    ],
    declarations: [
        ExerciseFacilityAppComponent,
        FacilitySearchComponent,
        PartnerComponent
    ],
    providers: [
        ExerciseFacilityService,
        FacilitySearchService,
        PartnerService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyappEntitytModule {}
