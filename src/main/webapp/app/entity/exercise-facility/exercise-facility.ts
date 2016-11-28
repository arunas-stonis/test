export class ExerciseFacility {
  id: number;
  createdAt: string = '2016-11-26T09:24:44.47Z'; //Date = Date(); //'2016-11-26T09:24:44.47Z'; //todo: change it to Date
  name: string = '';
  categoryId: number = 0;
  address: string = '';
  description: string = '';

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
}
