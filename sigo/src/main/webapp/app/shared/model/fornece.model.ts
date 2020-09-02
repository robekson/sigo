import { Moment } from 'moment';
import { IProduto } from 'app/shared/model/produto.model';

export interface IFornece {
  id?: number;
  quantidade?: number;
  data?: Moment;
  tamanho?: string;
  valor?: number;
  compras?: IProduto[];
  forneceId?: number;
}

export class Fornece implements IFornece {
  constructor(
    public id?: number,
    public quantidade?: number,
    public data?: Moment,
    public tamanho?: string,
    public valor?: number,
    public compras?: IProduto[],
    public forneceId?: number
  ) {}
}
