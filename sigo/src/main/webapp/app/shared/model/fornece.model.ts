import { Moment } from 'moment';
import { IMateriaPrima } from 'app/shared/model/materia-prima.model';

export interface IFornece {
  id?: number;
  quantidade?: number;
  data?: Moment;
  tamanho?: string;
  valor?: number;
  materiaPrimas?: IMateriaPrima[];
  fornecedorId?: number;
}

export class Fornece implements IFornece {
  constructor(
    public id?: number,
    public quantidade?: number,
    public data?: Moment,
    public tamanho?: string,
    public valor?: number,
    public materiaPrimas?: IMateriaPrima[],
    public fornecedorId?: number
  ) {}
}
