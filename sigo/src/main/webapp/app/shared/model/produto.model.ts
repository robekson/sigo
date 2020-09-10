import { ICompra } from 'app/shared/model/compra.model';

export interface IProduto {
  id?: number;
  referencia?: string;
  nome?: string;
  cores?: string;
  insumo?: string;
  materiaPrimaId?: number;
  vendaId?: number;
  compras?: ICompra[];
}

export class Produto implements IProduto {
  constructor(
    public id?: number,
    public referencia?: string,
    public nome?: string,
    public cores?: string,
    public insumo?: string,
    public materiaPrimaId?: number,
    public vendaId?: number,
    public compras?: ICompra[]
  ) {}
}
