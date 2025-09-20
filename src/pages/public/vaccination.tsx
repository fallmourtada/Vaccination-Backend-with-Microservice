import PageContainer from '@/components/shared/page-container';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Button } from '@/components/ui/button';
import { Badge } from '@/components/ui/badge';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { 
  Pagination, 
  PaginationContent, 
  PaginationItem, 
  PaginationLink, 
  PaginationNext, 
  PaginationPrevious 
} from '@/components/ui/pagination';
import { Calendar, CalendarIcon, CheckCircle2, Clock, Plus, Search, Syringe, User, AlertTriangle } from 'lucide-react';
import { useState } from 'react';
import { ModalProvider, useModal } from '@/components/shared/modal-provider';
import { useNotification } from '@/components/shared/app-notification';

// Types pour les vaccinations
interface Vaccination {
  id: string;
  patientId: string;
  patientNom: string;
  patientPrenom: string;
  vaccin: string;
  lot: string;
  dateVaccination: string;
  prochainRappel?: string;
  statut: 'termine' | 'planifie' | 'en_attente' | 'reporte';
  medecin: string;
  centre: string;
  notes?: string;
}

// Données simulées
const vaccinationsData: Vaccination[] = [
  {
    id: '1',
    patientId: 'p1',
    patientNom: 'Dupont',
    patientPrenom: 'Marie',
    vaccin: 'COVID-19 (Pfizer)',
    lot: 'LOT-CV-2024-001',
    dateVaccination: '2024-09-10',
    prochainRappel: '2025-03-10',
    statut: 'termine',
    medecin: 'Dr. Martin',
    centre: 'Centre de Santé Nord',
    notes: 'Aucune réaction adverse'
  },
  {
    id: '2',
    patientId: 'p2',
    patientNom: 'Bernard',
    patientPrenom: 'Jean',
    vaccin: 'Grippe saisonnière',
    lot: 'LOT-GR-2024-045',
    dateVaccination: '2024-09-12',
    statut: 'termine',
    medecin: 'Dr. Dubois',
    centre: 'Centre de Santé Sud'
  },
  {
    id: '3',
    patientId: 'p3',
    patientNom: 'Moreau',
    patientPrenom: 'Sophie',
    vaccin: 'COVID-19 (Moderna)',
    lot: 'LOT-CV-2024-002',
    dateVaccination: '2024-09-15',
    prochainRappel: '2025-03-15',
    statut: 'planifie',
    medecin: 'Dr. Martin',
    centre: 'Centre de Santé Nord'
  },
  {
    id: '4',
    patientId: 'p4',
    patientNom: 'Petit',
    patientPrenom: 'Lucas',
    vaccin: 'ROR (Rougeole-Oreillons-Rubéole)',
    lot: 'LOT-ROR-2024-012',
    dateVaccination: '2024-09-18',
    statut: 'en_attente',
    medecin: 'Dr. Legrand',
    centre: 'Centre de Santé Est'
  },
  {
    id: '5',
    patientId: 'p5',
    patientNom: 'Rousseau',
    patientPrenom: 'Emma',
    vaccin: 'Hépatite B',
    lot: 'LOT-HEP-2024-067',
    dateVaccination: '2024-09-20',
    prochainRappel: '2025-09-20',
    statut: 'termine',
    medecin: 'Dr. Dubois',
    centre: 'Centre de Santé Sud'
  },
  {
    id: '6',
    patientId: 'p6',
    patientNom: 'Leroy',
    patientPrenom: 'Antoine',
    vaccin: 'COVID-19 (Pfizer)',
    lot: 'LOT-CV-2024-003',
    dateVaccination: '2024-09-22',
    prochainRappel: '2025-03-22',
    statut: 'termine',
    medecin: 'Dr. Martin',
    centre: 'Centre de Santé Nord'
  },
  {
    id: '7',
    patientId: 'p7',
    patientNom: 'Garcia',
    patientPrenom: 'Chloe',
    vaccin: 'Grippe saisonnière',
    lot: 'LOT-GR-2024-046',
    dateVaccination: '2024-09-25',
    statut: 'planifie',
    medecin: 'Dr. Legrand',
    centre: 'Centre de Santé Est'
  },
  {
    id: '8',
    patientId: 'p8',
    patientNom: 'Roux',
    patientPrenom: 'Noah',
    vaccin: 'COVID-19 (Moderna)',
    lot: 'LOT-CV-2024-004',
    dateVaccination: '2024-09-28',
    prochainRappel: '2025-03-28',
    statut: 'reporte',
    medecin: 'Dr. Dubois',
    centre: 'Centre de Santé Sud'
  },
  {
    id: '9',
    patientId: 'p9',
    patientNom: 'Vincent',
    patientPrenom: 'Léa',
    vaccin: 'ROR (Rougeole-Oreillons-Rubéole)',
    lot: 'LOT-ROR-2024-013',
    dateVaccination: '2024-09-30',
    statut: 'en_attente',
    medecin: 'Dr. Martin',
    centre: 'Centre de Santé Nord'
  },
  {
    id: '10',
    patientId: 'p10',
    patientNom: 'Simon',
    patientPrenom: 'Louis',
    vaccin: 'Hépatite B',
    lot: 'LOT-HEP-2024-068',
    dateVaccination: '2024-10-02',
    prochainRappel: '2025-10-02',
    statut: 'termine',
    medecin: 'Dr. Legrand',
    centre: 'Centre de Santé Est'
  },
  {
    id: '11',
    patientId: 'p11',
    patientNom: 'Fabre',
    patientPrenom: 'Jade',
    vaccin: 'COVID-19 (Pfizer)',
    lot: 'LOT-CV-2024-005',
    dateVaccination: '2024-10-05',
    prochainRappel: '2025-04-05',
    statut: 'planifie',
    medecin: 'Dr. Dubois',
    centre: 'Centre de Santé Sud'
  },
  {
    id: '12',
    patientId: 'p12',
    patientNom: 'Blanc',
    patientPrenom: 'Gabriel',
    vaccin: 'Grippe saisonnière',
    lot: 'LOT-GR-2024-047',
    dateVaccination: '2024-10-08',
    statut: 'termine',
    medecin: 'Dr. Martin',
    centre: 'Centre de Santé Nord'
  },
  {
    id: '13',
    patientId: 'p13',
    patientNom: 'Guerin',
    patientPrenom: 'Alice',
    vaccin: 'COVID-19 (Moderna)',
    lot: 'LOT-CV-2024-006',
    dateVaccination: '2024-10-10',
    prochainRappel: '2025-04-10',
    statut: 'en_attente',
    medecin: 'Dr. Legrand',
    centre: 'Centre de Santé Est'
  },
  {
    id: '14',
    patientId: 'p14',
    patientNom: 'Muller',
    patientPrenom: 'Tom',
    vaccin: 'ROR (Rougeole-Oreillons-Rubéole)',
    lot: 'LOT-ROR-2024-014',
    dateVaccination: '2024-10-12',
    statut: 'planifie',
    medecin: 'Dr. Dubois',
    centre: 'Centre de Santé Sud'
  },
  {
    id: '15',
    patientId: 'p15',
    patientNom: 'Lopez',
    patientPrenom: 'Manon',
    vaccin: 'Hépatite B',
    lot: 'LOT-HEP-2024-069',
    dateVaccination: '2024-10-15',
    prochainRappel: '2025-10-15',
    statut: 'reporte',
    medecin: 'Dr. Martin',
    centre: 'Centre de Santé Nord'
  }
];

const statutConfig = {
  termine: { label: 'Terminé', color: 'bg-green-100 text-green-800 dark:bg-green-900/50 dark:text-green-300', icon: CheckCircle2 },
  planifie: { label: 'Planifié', color: 'bg-blue-100 text-blue-800 dark:bg-blue-900/50 dark:text-blue-300', icon: Calendar },
  en_attente: { label: 'En attente', color: 'bg-orange-100 text-orange-800 dark:bg-orange-900/50 dark:text-orange-300', icon: Clock },
  reporte: { label: 'Reporté', color: 'bg-red-100 text-red-800 dark:bg-red-900/50 dark:text-red-300', icon: AlertTriangle }
};

function VaccinationContent() {
  const { openModal } = useModal();
  const notification = useNotification();
  const [searchTerm, setSearchTerm] = useState('');
  const [statutFilter, setStatutFilter] = useState<string>('tous');
  const [vaccinFilter, setVaccinFilter] = useState<string>('tous');
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 10;

  // Remettre à la page 1 quand les filtres changent
  // useEffect(() => {
  //   setCurrentPage(1);
  // }, [searchTerm, statutFilter, vaccinFilter]);

  // Filtrage des vaccinations
  const filteredVaccinations = vaccinationsData.filter(vaccination => {
    const matchSearch = 
      vaccination.patientNom.toLowerCase().includes(searchTerm.toLowerCase()) ||
      vaccination.patientPrenom.toLowerCase().includes(searchTerm.toLowerCase()) ||
      vaccination.vaccin.toLowerCase().includes(searchTerm.toLowerCase());
    
    const matchStatut = statutFilter === 'tous' || vaccination.statut === statutFilter;
    const matchVaccin = vaccinFilter === 'tous' || vaccination.vaccin.includes(vaccinFilter);
    
    return matchSearch && matchStatut && matchVaccin;
  });

  // Pagination
  const totalPages = Math.ceil(filteredVaccinations.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const paginatedVaccinations = filteredVaccinations.slice(startIndex, startIndex + itemsPerPage);

  // Statistiques rapides
  const stats = {
    total: vaccinationsData.length,
    termine: vaccinationsData.filter(v => v.statut === 'termine').length,
    planifie: vaccinationsData.filter(v => v.statut === 'planifie').length,
    en_attente: vaccinationsData.filter(v => v.statut === 'en_attente').length
  };

  const handleCreateVaccination = () => {
    openModal('create-vaccination', {});
    notification.info({
      title: "Nouvelle vaccination",
      description: "Ouverture du formulaire de création de vaccination"
    });
  };

  const handleViewDetails = (vaccination: Vaccination) => {
    openModal('detail-vaccination', vaccination);
    notification.info({
      title: "Détails vaccination",
      description: `Consultation de la vaccination ${vaccination.vaccin} pour ${vaccination.patientPrenom} ${vaccination.patientNom}`
    });
  };

  return (
    <PageContainer 
      title="Gestion des Vaccinations" 
      subtitle="Suivi et administration des vaccins"
    >
      <div className="space-y-6">
        
        {/* Statistiques rapides */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
          <Card>
            <CardContent className="p-6">
              <div className="flex items-center space-x-3">
                <div className="p-2 bg-blue-100 dark:bg-blue-900/50 rounded-lg">
                  <Syringe className="h-6 w-6 text-blue-600 dark:text-blue-400" />
                </div>
                <div>
                  <p className="text-sm font-medium text-muted-foreground">Total</p>
                  <p className="text-2xl font-bold">{stats.total}</p>
                </div>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardContent className="p-6">
              <div className="flex items-center space-x-3">
                <div className="p-2 bg-green-100 dark:bg-green-900/50 rounded-lg">
                  <CheckCircle2 className="h-6 w-6 text-green-600 dark:text-green-400" />
                </div>
                <div>
                  <p className="text-sm font-medium text-muted-foreground">Terminés</p>
                  <p className="text-2xl font-bold text-green-600">{stats.termine}</p>
                </div>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardContent className="p-6">
              <div className="flex items-center space-x-3">
                <div className="p-2 bg-blue-100 dark:bg-blue-900/50 rounded-lg">
                  <Calendar className="h-6 w-6 text-blue-600 dark:text-blue-400" />
                </div>
                <div>
                  <p className="text-sm font-medium text-muted-foreground">Planifiés</p>
                  <p className="text-2xl font-bold text-blue-600">{stats.planifie}</p>
                </div>
              </div>
            </CardContent>
          </Card>

          <Card>
            <CardContent className="p-6">
              <div className="flex items-center space-x-3">
                <div className="p-2 bg-orange-100 dark:bg-orange-900/50 rounded-lg">
                  <Clock className="h-6 w-6 text-orange-600 dark:text-orange-400" />
                </div>
                <div>
                  <p className="text-sm font-medium text-muted-foreground">En attente</p>
                  <p className="text-2xl font-bold text-orange-600">{stats.en_attente}</p>
                </div>
              </div>
            </CardContent>
          </Card>
        </div>

        {/* Filtres et actions */}
        <Card>
          <CardHeader>
            <div className="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div>
                <CardTitle className="flex items-center space-x-2">
                  <Syringe className="h-5 w-5" />
                  <span>Liste des Vaccinations</span>
                </CardTitle>
                <CardDescription>
                  Gérez et suivez toutes les vaccinations des patients
                </CardDescription>
              </div>
              <Button onClick={handleCreateVaccination} className="flex items-center space-x-2">
                <Plus className="h-4 w-4" />
                <span>Nouvelle Vaccination</span>
              </Button>
            </div>

            {/* Filtres */}
            <div className="flex flex-col lg:flex-row gap-4 pt-4">
              <div className="flex-1">
                <div className="relative">
                  <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                  <Input
                    placeholder="Rechercher par patient ou vaccin..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="pl-10"
                  />
                </div>
              </div>
              
              <Select value={statutFilter} onValueChange={setStatutFilter}>
                <SelectTrigger className="w-full lg:w-48">
                  <SelectValue placeholder="Statut" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="tous">Tous les statuts</SelectItem>
                  <SelectItem value="termine">Terminé</SelectItem>
                  <SelectItem value="planifie">Planifié</SelectItem>
                  <SelectItem value="en_attente">En attente</SelectItem>
                  <SelectItem value="reporte">Reporté</SelectItem>
                </SelectContent>
              </Select>

              <Select value={vaccinFilter} onValueChange={setVaccinFilter}>
                <SelectTrigger className="w-full lg:w-48">
                  <SelectValue placeholder="Type de vaccin" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="tous">Tous les vaccins</SelectItem>
                  <SelectItem value="COVID-19">COVID-19</SelectItem>
                  <SelectItem value="Grippe">Grippe</SelectItem>
                  <SelectItem value="ROR">ROR</SelectItem>
                  <SelectItem value="Hépatite">Hépatite</SelectItem>
                </SelectContent>
              </Select>
            </div>
          </CardHeader>

          <CardContent>
            <div className="rounded-lg border">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>Patient</TableHead>
                    <TableHead>Vaccin</TableHead>
                    <TableHead>Date</TableHead>
                    <TableHead>Statut</TableHead>
                    <TableHead>Médecin</TableHead>
                    <TableHead>Prochain rappel</TableHead>
                    <TableHead className="text-right">Actions</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {paginatedVaccinations.map((vaccination) => {
                    const statutInfo = statutConfig[vaccination.statut];
                    const StatusIcon = statutInfo.icon;
                    
                    return (
                      <TableRow key={vaccination.id} className="hover:bg-muted/50">
                        <TableCell>
                          <div className="flex items-center space-x-3">
                            <Avatar className="h-8 w-8">
                              <AvatarImage src="" />
                              <AvatarFallback className="text-xs">
                                {vaccination.patientPrenom[0]}{vaccination.patientNom[0]}
                              </AvatarFallback>
                            </Avatar>
                            <div>
                              <div className="font-medium">
                                {vaccination.patientPrenom} {vaccination.patientNom}
                              </div>
                              <div className="text-sm text-muted-foreground">
                                ID: {vaccination.patientId}
                              </div>
                            </div>
                          </div>
                        </TableCell>
                        <TableCell>
                          <div>
                            <div className="font-medium">{vaccination.vaccin}</div>
                            <div className="text-sm text-muted-foreground">
                              Lot: {vaccination.lot}
                            </div>
                          </div>
                        </TableCell>
                        <TableCell>
                          <div className="flex items-center space-x-2">
                            <CalendarIcon className="h-4 w-4 text-muted-foreground" />
                            <span>{new Date(vaccination.dateVaccination).toLocaleDateString('fr-FR')}</span>
                          </div>
                        </TableCell>
                        <TableCell>
                          <Badge variant="secondary" className={statutInfo.color}>
                            <StatusIcon className="h-3 w-3 mr-1" />
                            {statutInfo.label}
                          </Badge>
                        </TableCell>
                        <TableCell>
                          <div className="flex items-center space-x-2">
                            <User className="h-4 w-4 text-muted-foreground" />
                            <span>{vaccination.medecin}</span>
                          </div>
                        </TableCell>
                        <TableCell>
                          {vaccination.prochainRappel ? (
                            <div className="flex items-center space-x-2">
                              <Clock className="h-4 w-4 text-muted-foreground" />
                              <span>{new Date(vaccination.prochainRappel).toLocaleDateString('fr-FR')}</span>
                            </div>
                          ) : (
                            <span className="text-muted-foreground">-</span>
                          )}
                        </TableCell>
                        <TableCell className="text-right">
                          <Button 
                            variant="ghost" 
                            size="sm"
                            onClick={() => handleViewDetails(vaccination)}
                          >
                            Voir détails
                          </Button>
                        </TableCell>
                      </TableRow>
                    );
                  })}
                </TableBody>
              </Table>
            </div>

            {filteredVaccinations.length === 0 && (
              <div className="text-center py-8">
                <Syringe className="h-12 w-12 text-muted-foreground mx-auto mb-4" />
                <h3 className="text-lg font-semibold">Aucune vaccination trouvée</h3>
                <p className="text-muted-foreground">
                  {searchTerm || statutFilter !== 'tous' || vaccinFilter !== 'tous' 
                    ? "Aucune vaccination ne correspond à vos critères de recherche."
                    : "Aucune vaccination enregistrée pour le moment."
                  }
                </p>
              </div>
            )}
          </CardContent>
        </Card>

        {/* Pagination */}
        {totalPages > 1 && (
          <div className="flex justify-center">
            <Pagination>
              <PaginationContent>
                <PaginationItem>
                  <PaginationPrevious 
                    href="#" 
                    onClick={(e) => {
                      e.preventDefault();
                      if (currentPage > 1) setCurrentPage(currentPage - 1);
                    }}
                  />
                </PaginationItem>
                {[...Array(totalPages)].map((_, i) => (
                  <PaginationItem key={i}>
                    <PaginationLink 
                      href="#" 
                      isActive={currentPage === i + 1}
                      onClick={(e) => {
                        e.preventDefault();
                        setCurrentPage(i + 1);
                      }}
                    >
                      {i + 1}
                    </PaginationLink>
                  </PaginationItem>
                ))}
                <PaginationItem>
                  <PaginationNext 
                    href="#" 
                    onClick={(e) => {
                      e.preventDefault();
                      if (currentPage < totalPages) setCurrentPage(currentPage + 1);
                    }}
                  />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </div>
        )}
      </div>
    </PageContainer>
  );
}

// Export par défaut du composant principal
export default function Vaccination() {
  return (
    <ModalProvider>
      <VaccinationContent />
    </ModalProvider>
  );
}
