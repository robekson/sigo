import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'funcionario',
        loadChildren: () => import('./funcionario/funcionario.module').then(m => m.SigoFuncionarioModule),
      },
      {
        path: 'normas',
        loadChildren: () => import('./normas/normas.module').then(m => m.SigoNormasModule),
      },
      {
        path: 'consultor',
        loadChildren: () => import('./consultor/consultor.module').then(m => m.SigoConsultorModule),
      },
      {
        path: 'fornecedor',
        loadChildren: () => import('./fornecedor/fornecedor.module').then(m => m.SigoFornecedorModule),
      },
      {
        path: 'produto',
        loadChildren: () => import('./produto/produto.module').then(m => m.SigoProdutoModule),
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.SigoClienteModule),
      },
      {
        path: 'compra',
        loadChildren: () => import('./compra/compra.module').then(m => m.SigoCompraModule),
      },
      {
        path: 'venda',
        loadChildren: () => import('./venda/venda.module').then(m => m.SigoVendaModule),
      },
      {
        path: 'fornece',
        loadChildren: () => import('./fornece/fornece.module').then(m => m.SigoForneceModule),
      },
      {
        path: 'materia-prima',
        loadChildren: () => import('./materia-prima/materia-prima.module').then(m => m.SigoMateriaPrimaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SigoEntityModule {}
