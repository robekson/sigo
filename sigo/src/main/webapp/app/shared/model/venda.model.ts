import { Moment } from 'moment';
import { IProduto } from 'app/shared/model/produto.model';

export interface IVenda {
  id?: number;
  quantidade?: number;
  data?: Moment;
  dataEntrega?: Moment;
  valor?: number;
  produtos?: IProduto[];
  funcionarioId?: number;
}

export class Venda implements IVenda {
  constructor(
    public id?: number,
    public quantidade?: number,
    public data?: Moment,
    public dataEntrega?: Moment,
    public valor?: number,
    public produtos?: IProduto[],
    public funcionarioId?: number
  ) {}
}
