import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { UIRouterModule } from 'ui-router-ng2';

import { MyappSharedModule } from '../shared';

import {
    ExerciseFacilityAppComponent,
    ExerciseFacilityService,
    entityState,
    exerciseFacilityState,
    PartnerComponent,
    PartnerService,
    partnerState
} from './';

let ENTITY_STATES = [
    entityState,
    exerciseFacilityState,
    partnerState
];

@NgModule({
    imports: [
        MyappSharedModule,
        UIRouterModule.forChild({ states: ENTITY_STATES })
    ],
    declarations: [
        ExerciseFacilityAppComponent,
        PartnerComponent
    ],
    providers: [
        ExerciseFacilityService,
        PartnerService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MyappEntitytModule {}
