import { ICompra } from 'app/shared/model/compra.model';

export interface ICliente {
  id?: number;
  nome?: string;
  cnpj?: string;
  telefone?: string;
  contato?: string;
  endereco?: string;
  cep?: string;
  compras?: ICompra[];
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nome?: string,
    public cnpj?: string,
    public telefone?: string,
    public contato?: string,
    public endereco?: string,
    public cep?: string,
    public compras?: ICompra[]
  ) {}
}
