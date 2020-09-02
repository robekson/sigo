import { Moment } from 'moment';
import { CategoryStatus } from 'app/shared/model/enumerations/category-status.model';

export interface INormas {
  id?: number;
  codigo?: string;
  titulo?: string;
  date?: Moment;
  status?: CategoryStatus;
}

export class Normas implements INormas {
  constructor(public id?: number, public codigo?: string, public titulo?: string, public date?: Moment, public status?: CategoryStatus) {}
}
